{ pkgs, lib, config, inputs, ... }:

{

  
  cachix.pull = [ "pre-commit-hooks" ];
  cachix.push = "validate-everything";
  
  languages.scala = {
    enable = true;
    package = pkgs.scala_3;
    sbt = {
      enable = true;
    };
  };

  enterShell = ''
  	echo JAVA_HOME=$JAVA_HOME
  '';
  
  enterTest = ''
  	sbt test
  '';
}
