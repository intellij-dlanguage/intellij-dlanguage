# Intellij Plugin to provide support for the [D Programming Language](http://dlang.org/)

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/kingsleyh/DLanguage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![ZenHub](https://raw.githubusercontent.com/ZenHubIO/support/master/zenhub-badge.png)](https://zenhub.io)


| Branch | Status |
| :--- | :--- |
| Master | [![Build Status](https://travis-ci.org/intellij-dlanguage/intellij-dlanguage.svg?branch=master)](https://travis-ci.org/intellij-dlanguage/intellij-dlanguage) |
| Develop | [![Build Status](https://travis-ci.org/intellij-dlanguage/intellij-dlanguage.svg?branch=develop)](https://travis-ci.org/intellij-dlanguage/intellij-dlanguage) |

This plugin brings D Language support to the Intellij IDE

## MeetUp Group

Come Along to the [London D Meetup!](http://www.meetup.com/London-D-Programmers/)

[![London D Meetup](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/london_d_programmers.png)](http://www.meetup.com/London-D-Programmers/)

## Advertising

I recently contributed as a technical reviewer for this great book about Learning D written by Michael Parker. Get it cheap by clicking on the images below:

[![Learning D](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/learningd.jpg)](http://bit.ly/1lSyjJ7)

[![Learning D](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/packt5.png)](http://bit.ly/1lSyjJ7)

## Background

I've just started learning the D programming language. I love the intellij IDE and wanted to have D support in my favourite IDE. This is the first
Intellij plugin I've written and is still a work in progress to get the full support working.

## Supported versions of IntelliJ / JDK

Since Jetbrains released IntelliJ 16 (the 2016.* versions) Java 8 is required to run the IDE on all platforms.

| Plugin Version | Intellij Versions |
| :--- | :--- |
| 1.12 | 2017.1 |
| 1.11 | 2016.3 |

#### A note to my users

I understand some of you may feel like the feature set is lacking in many areas. Some of the key areas are refactoring support, DUB as an external build tool, debugging and the full reference support that
provides go to declaration and go to implementation etc. Other users would prefer to see the plugin operational in other jetbrains products like Clion etc. Please be patient and continue to raise Issues for
features you would like and bugs you find. Remember I am a single developer (with a family) who has to work full time to support myself and them. All development on this project is done in my spare time
which is time I could be spending with my family. In order to build a highly capable, stable and robust plugin it takes a lot of time and the documentation for the the majority of the feature sets is very basic
if present at all. It often takes me several iterations of something to find the best way of doing something so be patient - I will implement all of the functionality required to make this plugin probably the
best IDE option for D. My vision is to have full support for D in intellij - but this is a project that will span years not weeks. So please support me by using the plugin and feeding back your experiences by
raising issues for feedback, feature requests and bugs. Thanks for your support :)

#### Latest Updates

* March 2017 - Released v1.12 to add compatibility with Intellij 2017.1 (#139)
* December 2016 - New features are in progress. In the meantime version 1.11 of the plugin adds compatibility with IntelliJ 2016.3
* 1st February 2016 - I'm spending some time using the plugin for one of my projects to get a feel for how it's working under some real usage. Development will resume after this period.
* 9th January 2016 - Fixed some bugs and working towards getting more of the intellij features working
* 27th December 2015 - Fixed DCD bugs and added dub init wizard to dub project creation
* 25th December 2015 - Added code formatting with DFmt
* 24th December 2015 - Added Syntax highlighting, DCD autocompletion, dub compile checking and Dscanner annotation
* 23rd December 2015 - Released an early test version to the intellij plugin repository
* 9th December 2015 - I have finished the first pass of the BNF grammar complete with extensive tests - the next step is to move onto basic syntax highlighting
* 21st October 2015 - Work has begun to re-organize and track the upcoming work as well an initial work on the BNF grammar - I have switched master to previous_master and current master is a currently not working bnf shell project
* 7th October 2015 - I have finally figured out several aspects of the BNF grammar and feel like I know enough now to begin a complete re-write of the parser using the grammarkit plugin for intellij. This will overcome the terrible performance issues caused by the integration of the DDT parser and will enable a longer term view of complete language support and lots of cool features that are just not possible to add in any other way.
* 29th March 2015 - I've not managed to fix the performance issue. The problem is that the integration of the DDT parser/lexer with intellij has to jump through many hoops to work correctly as intellij don't make it easy to use external parsers directly. I think the only option is to go back to writing the BNF grammer and JFlex lexer - which I have started working on - however I think this will take a few months of effort. So I'm pushing back the alpha release to after the summer.
* 5th Feb 2015 - I've had some other demands on my time so had to push back the alpha release until end of March 2015. On the plus side I've discovered
the major peformance issue that was holding me back - so I've started work on fixing it. Once I'm happy with the performance work will resume on more features.


## Installation

[Download](https://www.jetbrains.com/idea/) and install IntelliJ Community Edition (Free) or Ultimate (Paid)
From IntelliJ go to Preferences > Plugins > Browse Repositories and search For "D Language" - you will see 2 plugins - Choose the *DLanguage* one and click install and then click restart intellij

You can also download the plugin jar to your local disk here: [Jetbrains plugin repository](https://plugins.jetbrains.com/plugin/8115?pr=) and then in intelliJ
go to Preferences > Plugins > Install plugin from disk and choose the jar you downloaded

![installation](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/install.png)

## Quick Usage

#### Import an existing DUB project

  * If you have an existing dub project you can import it via File > Import Project
  * Only dub.json is supported currently - if you want dub.sdl support please raise an Issue for it

 ![Import](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/import-dub-project.png)

#### New Project with DUB

  * When you select this to create a new project with dub - if dub is on your path it will attempt to use dub init to create a new dub project for you. If dub is not on your path it will create a source directory and you will have to create your sdl/json dub file manually or rename/delete the source folder and then use dub init to recreate it
  * Or you can open an existing dub project by doing File -> Open 
  * Once a dub project is loaded there is right click menu option to run with dub, or you can use the run config - run with Dub
  * Before running go and configure the DTools in Preferences -> Other Settings -> D Tools

#### Basic DUB Dependency Support

  * There is very basic dub dependency support via Tools > Process D Libraries
  * It reads the dub dependencies and loads the libraries into the External Libraries

  ![Dub  Dependency](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/process-d-libs.png)

#### Basic D-Unit Test Runner Support

  * If you add the d-unit dependency to your dub.json [d-unit](http://code.dlang.org/packages/d-unit)
  * and then run Process D Libraries you will be able to run d-unit tests

  ![Test Support](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/d-unit-support.png)

#### Configure DTools

The best option is to do and get the following tools and build them according to their github page instructions:

  * [dub](http://code.dlang.org/download)
  * [DCD](https://github.com/Hackerpilot/DCD)
  * [DScanner](https://github.com/Hackerpilot/Dscanner)
  * [Dfmt](https://github.com/Hackerpilot/dfmt)

If you put these tools on your path you can go to Preferences -> Other Settings -> D Tools and just click autofind on each of the tools and it will find them. Otherwise you will need to select the correct path to the tool for each one.

There is a nice blog post about setting up the tools at: [www.samael.me.uk](http://www.samael.me.uk/2015/12/d-plugin-for-intellij-idea.html)

#### Configuring DCD

To configure DCD in the dcd-server add a comma separated list of paths that point to your libraries that you want to include in autocompletion.

For example:

        /Library/D/dmd/src/phobos,/Library/D/dmd/src/druntime/import,/Users/hendriki/.dub/packages/rainbow-master/src
        
This will add the phobos and druntime/import as well as my rainbow dub project. You can add other dub packages by adding the path to them. I should be able to autoconfigure this stuff in a future release.

**UPDATE**: all project files are now automatically added to DCD and if on OSX the D sources are also added automatically. Sorry Linux and Windows users but I don't know the paths to search on those platforms for the sources - I can get the dmd/dmd.exe file path and on OSX thats in the bin dir and the sources are in the src dir one level up. But no idea on Linux and Windows.

#### DCD Server Restart Action

I noticed that after a while the auto completion seemed to cause the editor to hang. As a first step I have added a menu action in Tools as the last option to Restart the DCD Server. This action will just kill the running process and restart it again which seems to fix the hanging issue. I will investigate further to find a way to prevent the hanging in the first place but this is a helpful restart action.

#### DFormat

To reformat D code use the shortcut or the menu item - Code -> Reformat Code with D Format (ctrl+alt+K) or on mac (cmd+alt+K)

#### DFix

To apply DFix to the a file use the shortcut or the menu item - Code -> Fix D code with DFi (ctrl+alt+M) or on mac (cmd+alt+M)

#### Syntax Highlighting

Go to Preferences -> Editor -> Colors and Fonts -> D File

You can customize the syntax highlighting colours here. Save as a new theme - and then untick the inherit from checkbox and this will allow you to choose a colour for each item.
Only the native items will show as changing in the code example. From function definition downwards the options are related to annotated highlighting which is based on the grammar and not the lexer. So these don't show as chanigng the code example but they do wok.
I created one similar to the sublime dark theme by using Darcula theme and customizing the colours here.



## Progress

Please see the development status on
[DLanguage ZenBoard](https://github.com/intellij-dlanguage/intellij-dlanguage#boards)


## Notes

I have seen a few other attempts at an intellij plugin for D. But none are complete yet. If another D plugin project advances
further than this one - I will probably switch my efforts to that one and drop this one.

In the immediate future I have decided to re-use the eclipse DDT D Lexer and Parser and integrate it with my intellij plugin. I have
done some research into creating grammars and parsers and at the current point in time I have neither the time or the skills to
write a lexer/parser or BNF Grammar for the D Language.

When I find some time and as a learning opportunity I will try to write the BNF Grammar from scratch using the intellij Grammar-Kit
and then I will also write the JFlex lexer by hand.
(I've never written either a grammar or a lexer before so will probably get some help in this area and it probably will take a long time)

I may also decide to re-write the eclipse DDT D lexer/parser within the intellij framework to better customize it for intellij - Again this
may or may not happen in this amazing future where I actually have time for things like this.

## Release

The first official release will be published on the Jetbrains plugin site when I feel like the plugin offers the minimum viable functionality as
contained in the Alpha Release milestone detailed above.

#### Release Schedule

|Release|Date|Content Overview|
| ------------- |:-------------:| -----:|
|Alpha| 31 Jan 2016| Create D files, Dub, Syntax highlighting, Run Configs, Annotations, Indexes and References|
|Beta| 31 Jul 2016| Improved features plus quick fixes|
|Gamma|31 Dec 2016| Debugger support|

## Screenshots

Here are some screenshots of my progress so far:

## Recent Screenshots

Dtools configuration

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/tool_settings.png)

Autocompletion using DCD

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/autocomplete.png)

Compile checking

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/compile_checking.png)

Dscanner Code Linting

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/dscanner_linting.png)

Goto Class

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/goto_class.png)

Configure Syntax Highlighting

![highlighting settings](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/highlighting_settings.png)

## Older Screenshots

Improved Syntax Highlighting with Annotator:

![improved highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/improved_syntax_highlighting.png)

Syntax Highlighting and PSI Structure:

![highlighting and psi structure](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/highlighting_and_psi_structure.png)

Create a new D project:

![create a new d project](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/create_new_d_project.png)

Module Settings - add D compiler:

![module settings add d compiler](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/module_settings_add_d_compiler.png)

Module Settings - set D compiler in project:

![module settings add compiler to project](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/module_settings_set_project_d_compiler.png)

Add new D file (from right click menu)

![right click add new d file](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/right_click_add_new_d_file.png)

D file editor:

![d file editor](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/d_file_editor.png)

Run Configuration menus:

![run configuration menus](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/run_configuration_menus.png)

Run Configuration create box:

![run configuration create box](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/run_configuration_create_box.png)

Run Configuration edit:

![run configuration edit](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/run_configuration_edit.png)

Run Configuration running:

![run configuration running](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/run_configuration_running.png)

PSI Tree and Highlighting:

![psi tree and highlighting](https://github.com/intellij-dlanguage/intellij-dlanguage/raw/master/.README/psi_structure.png)


## Develop

Interested in contributing to this D plugin please contact me at kingsley at masterthought dot net.
Contributions are accepted via fork and pull request but please co-ordinate with me so we don't duplicate effort.

## Instructions for Developers

read this document: [developing custom language plugins for intellij](https://confluence.jetbrains.com/display/IDEADEV/Developing+Custom+Language+Plugins+for+IntelliJ+IDEA)

also see this document for getting the environment setup: [plugin development](https://confluence.jetbrains.com/display/IDEADEV/PluginDevelopment)

this one is also helpful: [make a plugin in less than 30 mins](http://bjorn.tipling.com/how-to-make-an-intellij-idea-plugin-in-30-minutes)
### Setup

The project uses Gradle with the [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin). Simply use the gradle wrapper in the root of the project to build the plugin using the following:

```bash
./gradlew buildPlugin
```

You can also use the plugin to boot up a stand alone instance of Intellij with the plugin installed using:

```bash
./gradlew runIde
```
