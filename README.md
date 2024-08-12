Intellij D Language
===================

Support for the [D Programming Language](http://dlang.org/) within IntelliJ IDEA (planned Clion support is experimental. See [CLion issues](https://github.com/intellij-dlanguage/intellij-dlanguage/labels/Clion))

[![JetBrains IntelliJ plugins](https://img.shields.io/jetbrains/plugin/d/8115.svg)](https://plugins.jetbrains.com/plugin/8115)

| Branch | Status |
| :--- | :--- |
| Master | [![Build & Verify](https://github.com/intellij-dlanguage/intellij-dlanguage/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/intellij-dlanguage/intellij-dlanguage/actions/workflows/gradle.yml) [![Coverage Status](https://coveralls.io/repos/github/intellij-dlanguage/intellij-dlanguage/badge.svg?branch=master)](https://coveralls.io/github/intellij-dlanguage/intellij-dlanguage?branch=master) |
| Develop | [![Build & Verify](https://github.com/intellij-dlanguage/intellij-dlanguage/actions/workflows/gradle.yml/badge.svg?branch=develop)](https://github.com/intellij-dlanguage/intellij-dlanguage/actions/workflows/gradle.yml) [![Coverage Status](https://coveralls.io/repos/github/intellij-dlanguage/intellij-dlanguage/badge.svg?branch=develop)](https://coveralls.io/github/intellij-dlanguage/intellij-dlanguage?branch=develop) |

## Supported IntelliJ based IDEs:

The primary focus for the project is to support Intellij IDEA (both IC and IU). Some work has been done to try and support CLion but it's not currently usable. It may work on other IDEs such as AppCode, Android Studio, PyCharm, etc but this is not tested by the dev team.

We generally try to support the last years worth of IDE releases. The current build targets *Intellij 2023.3 - 2024.2*.

For a list of older releases and their supported IDE versions see our [Compatibility Matrix](https://github.com/intellij-dlanguage/intellij-dlanguage/wiki/Compatibility-Matrix).

## Installation

Assuming you have IntelliJ IDEA, CLion (or another Intellij based IDE installed), you can simply install the plugin via the IDE:

Go to *Settings* (Preferences on Mac) > *Plugins* > Browse Repositories and search For "D Language" (**you may see 2 plugins, Choose the DLanguage one**) then click install and restart IntelliJ

![installation](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/dlang-install-plugin.png)

You can also download the plugin jar to your local disk directly from the [Jetbrains plugin repository](https://plugins.jetbrains.com/plugin/8115) and then in IntelliJ go to Preferences > Plugins > Install plugin from disk and choose the jar you downloaded

**After the plugin has been installed you will need to set dmd up as project SDK and configure dub**

## A word on support, etc.

This plugin is maintained by a very small team of volunteers in their spare time. There are [issues that need to be resolved](https://github.com/intellij-dlanguage/intellij-dlanguage/issues) both to add new features and simply keep the plugin compatible with new versions of Intellij. As the project relies on the good will of its contributors, progress can sometimes take a while. Please consider this when creating new issues. You can help by using the plugin and raising issues for feature requests and bugs. Even better **get involved and submit pull requests**.

## Quick Usage

### Import an existing DUB project

If you have an existing dub project you can import it via File > Import Project

![Import](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/import-dub-project.png)

### New Project with DUB

When you select this to create a new project with dub, if dub is on your path, it will attempt to use _dub init_ to create a new dub project for you. If dub is not on your path it will create a source directory and you will have to create your sdl/json dub file manually or rename/delete the source folder and then use _dub init_ to recreate it. Alternatively you can open an existing dub project by doing: File -> Open

Once a dub project is loaded, there is right click menu option to run with dub, or you can use the run config
– run with Dub.

Before running go and configure the DTools in _Settings_ -> _Languages &amp; Frameworks_ -> _D Tools_.

### Basic DUB Dependency Support

There is very basic dub dependency support via Tools > Process D Libraries

It reads the dub dependencies and loads the libraries into the External Libraries

  ![Dub  Dependency](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/process-d-libs.png)

#### Basic D-Unit Test Runner Support

If you add the d-unit dependency to your dub.json [d-unit](http://code.dlang.org/packages/d-unit) and then run Process D Libraries you will be able to run d-unit tests

  ![Test Support](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/d-unit-support.png)

### Configure DTools

The best option is to do and get the following tools and build them according to their GitHub page instructions:

 * [dub](http://code.dlang.org/download)
 * [DCD](https://github.com/Hackerpilot/DCD)
 * [DScanner](https://github.com/Hackerpilot/Dscanner)
 * [Dfmt](https://github.com/Hackerpilot/dfmt)

If you put these tools on your path you can go to (_Settings_ -> _Languages &amp; Frameworks_ -> _D Tools_) and just click autofind on each of the tools and it will find them. Otherwise you will need to select the correct path to the tool for each one.

There is a nice blog post about setting up the tools at: [www.samael.me.uk](http://www.samael.me.uk/2015/12/d-plugin-for-intellij-idea.html)

### Configuring DCD

To configure DCD in the dcd-server add a comma separated list of paths that point to your libraries that you want to include in autocompletion.

For example:

        /Library/D/dmd/src/phobos,/Library/D/dmd/src/druntime/import,/Users/hendriki/.dub/packages/rainbow-master/src

This will add the phobos and druntime/import as well as my rainbow dub project. You can add other dub packages by adding the path to them. I should be able to autoconfigure this stuff in a future release.

**UPDATE**: all project files are now automatically added to DCD and if on MacOS/OSX/macOS the D sources are also added automatically. The position on Linux and Windows is not currently clear since this work was done on OSX.

### DCD Server Restart Action

I noticed that after a while the auto completion seemed to cause the editor to hang. As a first step I have added a menu action in Tools as the last option to Restart the DCD Server. This action will just kill the running process and restart it again which seems to fix the hanging issue. I will investigate further to find a way to prevent the hanging in the first place but this is a helpful restart action.

### DFormat

To reformat D code use the shortcut or the menu item - Code -> Reformat Code with D Format (ctrl+alt+K) or on mac (cmd+alt+K)

### Syntax Highlighting

Go to Preferences -> Editor -> Colors and Fonts -> D File

You can customize the syntax highlighting colours here. Save as a new theme - and then untick the inherit from checkbox and this will allow you to choose a colour for each item. Only the native items will show as changing in the code example. From function definition downwards the options are related to annotated highlighting which is based on the grammar and not the lexer. So these don't show as changing the code example but they do work.

## Screenshots

Here are some screenshots of progress to date:

### Recent Screenshots

Dtools configuration

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/tool_settings.png)

Autocompletion using DCD

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/autocomplete.png)

Compile checking

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/compile_checking.png)

Dscanner Code Linting

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/dscanner_linting.png)

Goto Class

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/goto_class.png)

Configure Syntax Highlighting

![highlighting settings](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/highlighting_settings.png)

### Older Screenshots

Improved Syntax Highlighting with Annotator:

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/improved_syntax_highlighting.png)

Syntax Highlighting and PSI Structure:

![highlighting and psi structure](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/highlighting_and_psi_structure.png)

Create a new D project:

![create a new d project](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/create_new_d_project.png)

Module Settings - add D compiler:

![module settings add d compiler](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/module_settings_add_d_compiler.png)

Module Settings - set D compiler in project:

![module settings add compiler to project](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/module_settings_set_project_d_compiler.png)

Add new D file (from right click menu)

![right click add new d file](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/right_click_add_new_d_file.png)

D file editor:

![d file editor](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/d_file_editor.png)

Run Configuration menus:

![run configuration menus](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/run_configuration_menus.png)

Run Configuration create box:

![run configuration create box](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/run_configuration_create_box.png)

Run Configuration edit:

![run configuration edit](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/run_configuration_edit.png)

Run Configuration running:

![run configuration running](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/run_configuration_running.png)

PSI Tree and Highlighting:

![psi tree and highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/develop/.README/psi_structure.png)


## Develop

Contributions are accepted via fork and pull request but please co-ordinate via issues so we don't duplicate effort.

## Instructions for Developers

read this document: [developing custom language plugins for IntelliJ](https://confluence.jetbrains.com/display/IDEADEV/Developing+Custom+Language+Plugins+for+IntelliJ+IDEA)

also see this document for getting the environment setup: [plugin development](https://confluence.jetbrains.com/display/IDEADEV/PluginDevelopment)

this one is also helpful: [make a plugin in less than 30 mins](http://bjorn.tipling.com/how-to-make-an-intellij-idea-plugin-in-30-minutes)

### Setup

The project uses Gradle with the [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin). Simply use the Gradle wrapper in the root of the project to build the plugin using the following:

```bash
./gradlew buildPlugin
```

You can also use the plugin to boot up a stand alone instance of IntelliJ with the plugin installed using:

```bash
./gradlew runIde
```

or to run against a specific Intellij version use: 

```bash
./gradlew runIde -PideaVersion=2018.3.5
```

### Copyright Notice

This plugin uses portions of code from another project. There original copyright statement is below:

```
Copyright (c) 2017 Patrick Scheibe
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
