#FSquaDRA

##Description
FSquaDRA is a tool for detection of repackaged Android applications. The 
approach is based on the idea that repackaged applications want to maintain 
"look and feel" of the originals.

Our tool computes Jaccard similarity over the set of digests of files 
included into Android package file. We use the digests precomputed during the 
application signing, thus, significantly improving the speed of apk comparison.

This work has been done in the University of Trento.




##Publication
The results of our research will be presented on the 28th Annual IFIP WG 11.3 
Working Conference on Data and Applications Security and Privacy. Currently, 
please use the following bibtex reference to cite our paper:

```
@inproceedings{Fsquadra_Zhauniarovich2014,
    author = {Zhauniarovich, Yury and Gadyatskaya, Olga and Crispo, Bruno and La Spina, Francesco and Moser, Ermanno},
    title = {{FSquaDRA: Fast Detection of Repackaged Applications}},
    booktitle = {Proceedings of the 28th Annual IFIP WG 11.3 Working Conference on Data and Applications Security and Privacy},
    series = {DBSec '14},
    pages = {131--146},
    year = {2014},
    note = {to appear},
}
```




##Usage
We would recommend to build a jar file from the project. In this case, the tool 
can be run in the following way:

```
java -jar fsquadra.jar <path1> <path2> -o=<result_file>
```

where *<path1>* is a path to the first apk file or folder with apk files, 
*<path2>* is a path to the second apk file or folder with apk files. The tool 
will take all files from the *<path1>* and compare them pair wise with the files 
in *<path2>*. *<path2>* may be absent, in this case, FSquaDRA will compare pair 
wise all the files inside *<path1>* folder. 

The results of the comparison will be written into *<result_file>* in csv 
format in the following way:

```
apkName1;apkName2;numberOfFilesInApk1;numberOfFilesInApk2;jaccardSimilarity;ifTheCertificatesAreTheSame;
```




##Libraries
The tool uses [jCommander](http://jcommander.org/) library to parse command 
line arguments.




##License
The tool is distributed under Apache-2.0 license. The citation of the paper is 
highly appreciated.
