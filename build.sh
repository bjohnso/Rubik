[ -d build ] && rm -rf build
find . -name *.java > result.txt
sort result.txt > sources.txt
mkdir build
javac src/main/java/com/rubix/Rubix.java -sourcepath @result.txt -d build
rm -rf result.txt
rm -rf sources.txt