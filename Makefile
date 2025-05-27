clean:
	@rm -fr bin
compile: clean 
	@find -wholename "*.java" > sources; javac -d bin @sources; rm -fr sources
exec: compile
	@java -cp bin app.gui.Main
