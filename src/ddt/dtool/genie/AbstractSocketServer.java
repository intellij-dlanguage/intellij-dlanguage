/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.genie;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import ddt.melnorme.utilbox.concurrency.SafeRunnable;
import ddt.dtool.genie.GenieServer.GenieConnectionHandler;
import ddt.dtool.util.StatusException;

/** 
 * Helper for Genie Server, manages a basic TCP socket server.
 */
public abstract class AbstractSocketServer {
	
	protected static final int MAX_CONNECTIONS = 256;
	
	protected final ServerSocket serverSocket;
	protected final int portNumber;
	protected final CountDownLatch terminationLatch = new CountDownLatch(1);
	protected final Semaphore connectionsSemaphore = new Semaphore(MAX_CONNECTIONS);
	
	
	public AbstractSocketServer(int portNumber) throws StatusException {
		this.serverSocket = createServerSocket(portNumber);
		this.portNumber = serverSocket.getLocalPort();
	}
	
	protected ServerSocket createServerSocket(int portNumber) throws StatusException {
		try {
			return new ServerSocket(portNumber);
		} catch (IOException ioe) {
			throw new StatusException("Error creating socket on port " + portNumber + ".", ioe);
		}
	}
	
	public int getServerPortNumber() {
		return portNumber;
	}
	
	public abstract void logMessage(String message);
	public abstract void logError(String message, Throwable throwable);
	public abstract void handleInternalError(Throwable throwable);
	
	public void runServer() {
		try {
			while(true) {
				Socket clientSocket = serverSocket.accept();
				logMessage("New connection from: " + clientSocket.getRemoteSocketAddress().toString());
				handleNewClientConnection(clientSocket);
			}
		} catch (SocketException se) {
			assertTrue(serverSocket.isClosed());
		} catch (IOException ioe) {
			logError("Unexpected exception during socket accept: " , ioe);
		} finally {
			closeServerSocket();
			logMessage("Server socket closed.");
			terminationLatch.countDown();
		}
	}
	
	protected void closeServerSocket() {
		try {
			logMessage("Closing server socket.");
			serverSocket.close();
		} catch (IOException e) {
			logError("Error trying to close socket: ", e);
		}
	}
	
	public int getActiveConnections() {
		return MAX_CONNECTIONS - connectionsSemaphore.availablePermits();
	}
	
	protected void handleNewClientConnection(Socket clientSocket) {
		connectionsSemaphore.acquireUninterruptibly();
		ConnectionHandlerRunnable genieConnectionHandler = createConnectionHandlerRunnable(clientSocket);
		createHandlerThread(genieConnectionHandler).start();
	}
	
	protected Thread createHandlerThread(ConnectionHandlerRunnable genieConnectionHandler) {
		return new Thread(genieConnectionHandler);
	}
	
	protected abstract GenieConnectionHandler createConnectionHandlerRunnable(Socket clientSocket);
	
	public abstract class ConnectionHandlerRunnable extends SafeRunnable {
		
		protected final Socket clientSocket;
		
		public ConnectionHandlerRunnable(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		protected void handleUncaughtException(Throwable e) {
			handleInternalError(e);
			assertFail();
		}
		
		@Override
		protected void safeRun() {
			handleConnection();
		}
		
		public void handleConnection() {
			try {
				handleConnectionStream();
			} catch (IOException e) {
				logError("IO error during connection handling: ", e);
			} finally {
				logMessage("Closing client connection : " + clientSocket.getRemoteSocketAddress().toString());
				try {
					clientSocket.close();
				} catch (IOException e) {
					logError("Error trying to close client socket: ", e);
				}
				connectionsSemaphore.release();
			}
		}
		
		protected abstract void handleConnectionStream() throws IOException;
		
	}
	
}