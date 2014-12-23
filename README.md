# Intellij Plugin to provide support for the [D Programming Language](http://dlang.org/)

This plugin brings D Language support to the Intellij IDE

## Background

I've just started learning the D programming language. I love the intellij IDE and wanted to have D support in my favourite IDE. This is the first
Intellij plugin I've written and is still a work in progress to get the full support working.

## Progress

I intend to complete the following items over the next 3 weeks:

#### Working

1. New D Project - working
2. New D File - working
3. Module Settings - working
4. Run Configurations for Code - working
5. DDT Lexer integration - working
6. DDT Parser integration - working
7. Syntax highlighting - working

#### In Progress
8. Indexes and References - in progress

#### To Do
9. Run Configurations for Tests - todo
10. Annotations - todo
11. Help and QuickFixes - todo
11. Debugger - todo

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

The first official release will be published on the Jetbrains plugin site when I feel like the plugin offers the minimum viable functionality.
That is:

1. Grammar/Lexer
2. Syntax Highlighting
4. Add D project
5. New D file
6. SDK
7. Run Configuration

## Screenshots

Here are some screenshots of my progress so far:

## Recent Screenshots

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