unittest
{
	testScene = new Scene
	(
		 "TestScene : Test",
		 sceneDescriptions["TestScene"],
		 connectDescriptions["TestScene"],
		 delegate(Scene scene)
		 {
			 import std.stdio;

			 if (!scene.alreadyEntered)
			 {
				 fwriteln("This is a test. This is a test. This is a test. This is a test. This is a test. Test12.");
				 auto p = cast(Portal)sceneManager.previousScene;
				 scene.destroyCurrentScript();
			 }
		 }
	);
}
