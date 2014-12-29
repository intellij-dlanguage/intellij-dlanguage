# Intellij Plugin to provide support for the [D Programming Language](http://dlang.org/)

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/kingsleyh/DLanguage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![HuBoard badge](http://img.shields.io/badge/Hu-Board-7965cc.svg)](https://huboard.com/kingsleyh/DLanguage#/)

This plugin brings D Language support to the Intellij IDE

## MeetUp Group

Come Along to the [London D Meetup!](http://www.meetup.com/London-D-Programmers/)

![meet up group]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/london_d_programmers.png)

## Background

I've just started learning the D programming language. I love the intellij IDE and wanted to have D support in my favourite IDE. This is the first
Intellij plugin I've written and is still a work in progress to get the full support working.

## Supported JDK

I personally use a 1.8 JDK but the majority of users should be on a 1.7 JDK so I have written this plugin at the 1.7 language level. It will not work with a 1.6 JDK because
it uses features of 1.7. If you are an OSX user like me - then you will need to Run intellij using 1.7 or 1.8 to use this plugin by doing the following:

To force running under JDK 1.7 edit /Applications/<Product>.app/Contents/Info.plist file, change JVMVersion from 1.6* to 1.7* :

```xml
<key>JVMVersion</key>
<string>1.7*</string>
```

## Progress

Please see the development status on
[DLanguage HuBoard](https://huboard.com/kingsleyh/DLanguage#/)

#### Alpha Release (end of Jan 2015)

1. New D Project
2. New D File
3. Module Settings
4. Run Configurations for Code
5. DDT Lexer integration
6. DDT Parser integration
7. Syntax highlighting
8. Indexes and References
9. Run Configurations for Tests
10. Annotations
11. Code Completion
12. DUB Support
13. Help and QuickFixes

#### Beta Release (end of Feb 2015)

14. Debugger

## Things I might do in the future

1. BNF Grammar
2. JFlex Lexer
3. Re-write DDT lexer/parser in intellij framework

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

## Screenshots

Here are some screenshots of my progress so far:

## Recent Screenshots

Improved Syntax Highlighting with Annotator:

![improved highlighting]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/improved_syntax_highlighting.png)

Syntax Highlighting and PSI Structure:

![highlighting and psi structure]
(https://github.com/kingsleyh/DLanguage/raw/master/.README/highlighting_and_psi_structure.png)

## Older Screenshots

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

this one is also helpful: [make a pluing in less than 30 mins](http://bjorn.tipling.com/how-to-make-an-intellij-idea-plugin-in-30-minutes)
### Setup

1. Download and install the community edition of intellij - [here](https://www.jetbrains.com/idea/download/)
2. Download the intellij community edition source code - git clone --depth 1 https://github.com/JetBrains/intellij-community.git (use depth 1 to reduce history otherwise it will take a long time)
3. Open this plugin project in intellij
4. In the module setting (F4) set the SDK to the path of the community edition source code JDK under the intellij platform plugin SDK

