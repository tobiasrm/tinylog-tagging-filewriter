# tinylog tagging filewriter
**A [tinylog](http://www.tinylog.org) filewriter extension to remove arbitrary data defined via custom tags from the log entry.**

The main objective is the use in combination with [tinylog-coloredconsole](https://github.com/tobiasrm/tinylog-coloredconsole), which allows arbitrary colorized logs through custom tags. With *tinylog-tagging-filewriter*, you can remove (or replace) those tags before writing the log to file.

## Features
The tinylog-tagging-filewriter extends tinylog with the `tagging-filewriter` file writer. It allows you to define up to 10 custom tags (an arbitrary string), which are replaced with the corresponding custom parameter. Thus, you can generate some tags in your source-code to further processing (e.g. via the [tinylog-coloredconsole](https://github.com/tobiasrm/tinylog-coloredconsole)) but replaced custom content for file writing.

The following listing outlines the defition of custom tags/parameters. The example will remove *customTag1* and *customTag2* before file writing (*customTag2* via non-provided content, i.e. being empty, thus reducing config size), while *customTag3* is replaced with "==3==".
 
```  
DEFINITION:
   tinylog.writer               = tagging-filewriter
   tinylog.writer.customTag1    = <custom tag>
   tinylog.writer.customParam1  = <content to replace customTag1>
   
EXAMPLE:    
   tinylog.writer               = tagging-filewriter
   tinylog.writer.customTag1    = [[customTag1]]
   tinylog.writer.customParam1  = 
   tinylog.writer.customTag2    = [[customTag2]]
   #tinylog.writer.customParam2 = 
   tinylog.writer.customTag3    = [[customTag3]]
   tinylog.writer.customParam3  = ==3==
   < and so on ... up to 10 tag/param entries>
``` 

The following screenshots show the listing example with some lorem ipsum text (and those three tags). 

**Console output (sysout in eclipse)**:

![](https://github.com/tobiasrm/tinylog-tagging-filewriter/blob/master/files/screenshot_sysout.png?raw=true) 

**Log file entry**:

![](https://github.com/tobiasrm/tinylog-tagging-filewriter/blob/master/files/screenshot_logfile.png?raw=true) 

## Try it out
You can reproduce the colored log demo by running  `mvn clean install`  and then  `java -jar target/tinylog-tagging-filewriter-1.3.1-executable.jar` 

Uncomment the desired logging of the main method and corresponding [tinylog.properties](https://github.com/tobiasrm/tinylog-tagging-filewriter/blob/master/src/main/resources/tinylog.properties) config for the listed example. 
 
## Maven artifact
Add the following dependency to your pom.xml to use the tinylog-tagging-filewriter in your Maven project:

```
<dependency>
	<groupId>com.github.tobiasrm</groupId>
	<artifactId>tinylog-tagging-filewriter</artifactId>
	<version>1.3.1</version>
</dependency>
```

Note: To make sure your IDE takes all files into account (notably the TaggingFileWriter class), clean and rebuild your project.

## Comments
- **Versioning**. The versioning uses the original tinylog versions for clarity about the underlying libary, e.g. tinylog-tagging-filewriter in version 1.3.1 uses tinylog v1.3.1 (see [pom.xml](https://github.com/tobiasrm/tinylog-tagging-filewriter/blob/master/pom.xml)). If needed, you may simply exclude it and use another tinylog version.


## Other tinylog writer extensions
See also my other tinylog writer extension projects:

- [tinylog-coloredconsole](https://github.com/tobiasrm/tinylog-coloredconsole) for colored log-level and arbitrary source-code generated data through custom tags 
- [tinylog-tagging-rollingfilewriter](https://github.com/tobiasrm/tinylog-tagging-rollingfilewriter) extension to remove custom strings (e.g. the tinylog-coloredconsole custom tags) before writing to file (based on rollingfilewriter)
- [tinylog-singlelevel-cw](https://github.com/tobiasrm/tinylog-singlelevel-cw) for console writers being restricted to their levels (e.g. only print trace, warn and error) to support debugging

