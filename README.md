# Intellij Plugin to provide support for the [D Programming Language](http://dlang.org/)

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/kingsleyh/DLanguage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![ZenHub] (https://raw.githubusercontent.com/ZenHubIO/support/master/zenhub-badge.png)] (https://zenhub.io)
[![Build Status](https://travis-ci.org/kingsleyh/DLanguage.svg?branch=master)](https://travis-ci.org/kingsleyh/DLanguage)

This plugin brings D Language support to the Intellij IDE

## MeetUp Group

Come Along to the [London D Meetup!](http://www.meetup.com/London-D-Programmers/)

[![London D Meetup](https://github.com/kingsleyh/DLanguage/raw/master/.README/london_d_programmers.png)](http://www.meetup.com/London-D-Programmers/)

## Advertising

I recently was a technical review for this great book about Learning D written by Michael Parker. Get it cheap by clicking on the images below:

[![Learning D](https://github.com/kingsleyh/DLanguage/raw/master/.README/learningd.png)](http://bit.ly/1lSyjJ7)

[![Learning D](https://github.com/kingsleyh/DLanguage/raw/master/.README/packt5.png)](http://bit.ly/1lSyjJ7)

## Background

I've just started learning the D programming language. I love the intellij IDE and wanted to have D support in my favourite IDE. This is the first
Intellij plugin I've written and is still a work in progress to get the full support working.

## Supported JDK

I personally use a 1.8 JDK but the majority of users should be on a 1.7 JDK so I have written this plugin at the 1.7 language level. It will not work with a 1.6 JDK because
it uses features of 1.7. If you are an OSX user like me - then you will need to Run intellij using 1.7 or 1.8 to use this plugin by doing the following:

To force running under JDK 1.7 copy `/Applications/<Product>.app/Contents/bin/idea.properties` to `~/Library/Preferences/<Product>/` and
change the JVMVersion key to 1.7*:

```
JVMVersion=1.7*
```

## Installation

[Download](https://www.jetbrains.com/idea/) and install IntelliJ Community Edition (Free) or Ultimate (Paid)
From IntelliJ go to Preferences > Plugins > Browse Repositories and search For "D Language" - you will see 2 plugins - Choose the DLanguage Support one and click install and then click restart intellij

You can also download the plugin jar to your local disk here: [Jetbrains plugin repository](https://plugins.jetbrains.com/plugin/8115?pr=) and then in intelliJ
go to Preferences > Plugins > Install plugin from disk and choose the jar you downloaded

![installation]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/install.png)

## Quick Usage

#### Open existing DUB project

  * File -> Open then choose your existing dub project folder
  * After the project loads - right click on the module (first item in the project tree on left pane) and choose Module Settings (f4)
  * In Module Setting - in the right pane in the Sources tab - locate the source directory in the tree and click it - and then click the blue sources folder at the top of the pane where it says Mark as
  * That will mark the source folder as a Source folder in intellij - and it will go blue - now intellij know where to find the source code for your dub module and you can use the run configurations now

#### New Project with DUB

  * When you select this to create a new project with dub - if dub is on your path it will attempt to use dub init to create a new dub project for you. If dub is not on your path it will create a source directory and you will have to create your sdl/json dub file manually or rename/delete the source folder and then use dub init to recreate it
  * Or you can open an existing dub project by doing File -> Open 
  * Once a dub project is loaded there is right click menu option to run with dub, or you can use the run config - run with Dub
  * Before running go and configure the DTools in Preferences -> Other Settings -> D Tools
  
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
[DLanguage ZenBoard](https://github.com/kingsleyh/DLanguage#boards)

#### Latest Updates

* 27th December 2015 - Fixed DCD bugs and added dub init wizard to dub project creation
* 25th December 2015 - Added code formatting with DFmt
* 24th December 2015 - Added Syntax highlighting, DCD autocompletion, dub compile checking and Dscanner annotation
* 23rd December 2015 - Released an early test version to the intellij plugin repository
* 9th December 2015 - I have finished the first pass of the BNF grammar complete with extensive tests - the next step is to move onto basic syntax highlighting
* 21st October 2015 - Work has begun to re-organize and track the upcoming work as well an initial work on the BNF grammar - I have switched master to previous_master and current master is a currently not working bnf shell project
* 7th October 2015 - I have finally figured out several aspects of the BNF grammar and feel like I know enough now to begin a complete re-write of the parser using the grammarkit plugin for intellij. This will overcome the terrible performance issues caused by the integration of the DDT parser and will enable a longer term view of complete language support and lots of cool features that are just not possible to add in any other way.
* 29th March 2015 - I've not managed to fix the performance issue. The problem is that the integration of the DDT parser/lexer with intellij has to jump through many hoops to work correctly as intellij don't make it easy to use external parsers directly. I think the only option is to go back to writing the BNF grammer and JFlex lexer - which I have started working on - however I think this will take a few months of effort. So I'm pushing back the alpha release to after the summer.
*  5th Feb 2015 - I've had some other demands on my time so had to push back the alpha release until end of March 2015. On the plus side I've discovered
the major peformance issue that was holding me back - so I've started work on fixing it. Once I'm happy with the performance work will resume on more features.

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

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/tool_settings.png)

Autocompletion using DCD

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/autocomplete.png)

Compile checking

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/compile_checking.png)

Dscanner Code Linting

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/dscanner_linting.png)

Goto Class

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/goto_class.png)

Configure Syntax Highlighting

![highlighting settings]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/highlighting_settings.png)

## Older Screenshots

Improved Syntax Highlighting with Annotator:

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/improved_syntax_highlighting.png)

Syntax Highlighting and PSI Structure:

![highlighting and psi structure]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/highlighting_and_psi_structure.png)

Create a new D project:

![create a new d project]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/create_new_d_project.png)

Module Settings - add D compiler:

![module settings add d compiler]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/module_settings_add_d_compiler.png)

Module Settings - set D compiler in project:

![module settings add compiler to project]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/module_settings_set_project_d_compiler.png)

Add new D file (from right click menu)

![right click add new d file]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/right_click_add_new_d_file.png)

D file editor:

![d file editor]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/d_file_editor.png)

Run Configuration menus:

![run configuration menus]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/run_configuration_menus.png)

Run Configuration create box:

![run configuration create box]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/run_configuration_create_box.png)

Run Configuration edit:

![run configuration edit]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/run_configuration_edit.png)

Run Configuration running:

![run configuration running]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/run_configuration_running.png)

PSI Tree and Highlighting:

![psi tree and highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/psi_structure.png)


## Develop

Interested in contributing to this D plugin please contact me at kingsley at masterthought dot net.
Contributions are accepted via fork and pull request but please co-ordinate with me so we don't duplicate effort.

## Instructions for Developers

read this document: [developing custom language plugins for intellij](https://confluence.jetbrains.com/display/IDEADEV/Developing+Custom+Language+Plugins+for+IntelliJ+IDEA)

also see this document for getting the environment setup: [plugin development](https://confluence.jetbrains.com/display/IDEADEV/PluginDevelopment)

this one is also helpful: [make a plugin in less than 30 mins](http://bjorn.tipling.com/how-to-make-an-intellij-idea-plugin-in-30-minutes)
### Setup

1. Download and install the community edition of intellij - [here](https://www.jetbrains.com/idea/download/)
2. Download the intellij community edition source code - git clone --depth 1 https://github.com/JetBrains/intellij-community.git (use depth 1 to reduce history otherwise it will take a long time)
3. Open this plugin project in intellij
4. In the module setting (F4) set the SDK to the path of the community edition JDK under the intellij platform plugin SDK
5. Optionally add the community edition source to the source code paths in the module settings to allow access to the core code while debugging etc
