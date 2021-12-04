rem "C:\Program Files\Java\jdk1.8.0_291\bin\javac.exe" main.java

dir /s /B *.java > sources.txt
"C:\Program Files\Java\jdk1.8.0_291\bin\javac.exe" @sources.txt
