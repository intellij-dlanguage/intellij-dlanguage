How to contribute to Intellij D Language
========================================

We're a small team so all contributions are appreciated be they bug reports, help with testing or providing code/translations.

## Creating Issues

Before opening a new issue please do the following:

 - Check existing issues (either open or closed) to avoid creating a duplicate issue.
 - If raising a bug please include the version of IntelliJ you are using and the version of the plugin along with any stack trace or screenshots that will help us replicate the problem. If you've cloned the repository please confirm which branch you are on.

## Prerequisite knowledge

If you want to get stuck into the code then you should already be comfortable with Java and be familiar with [Gradle](https://gradle.org/). Having some experience with [Kotlin](https://kotlinlang.org/) is useful as new features should ideally be done using Kotlin.

Make sure you can build and run the plugin locally. Simply running `./gradlew :runIde` in the project root is enough to get up and running if you're setup for doing Java development.

## Get familiar with IntelliJ Platform

The IntelliJ API can feel pretty horrible at times and [their documentation](http://www.jetbrains.org/intellij/sdk/docs/welcome.html) is lacking a lot of information. It's a good idea to look through the source of other plugins for getting an understanding of how things work. The following are some examples that I've found useful:

 - [intellij-plugins](https://github.com/JetBrains/intellij-plugins)
 - [go-lang-idea-plugin](https://github.com/go-lang-plugin-org/go-lang-idea-plugin)
 - [intellij-rust](https://github.com/intellij-rust/intellij-rust)

## Contributing to the code base

So assuming you've forked the repo and cloned it to your development machine, please make sure that you have the **develop** branch checked out (and up to date) before making any changes. The develop branch should always have the most recent changes and any PR should be made to this branch.

 - Commits should relate to a github issue, please make sure the issue number is in the commit message prefixed with a hash so that it can be viewed directly from the issue.
 - For small changes making them in develop will be fine but for anything substantial please make a new branch prefixed with either _feature/_ or _bugfix/_ so if adding a german translation the branch name coule be `feature/german_translation`.
 - Please keep the commits relevant to the feature/bugfix being worked on. Eg: A pull request from a branch named `bugfix/fix-nullpointer-in-DCommenter` shouldn't include commits for other unrelated changes.
 - Please keep in mind that someone will have to review the pull request before it's merged so if you are about to push multiple consecutive small commits consider squashing them (if appopriate) as it can make it easier for review.
 
