{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    devshell.url = "github:numtide/devshell";

    flake-parts.inputs.nixpkgs-lib.follows = "nixpkgs";
    devshell.inputs.nixpkgs.follows = "nixpkgs";
  };

  outputs = inputs@{ flake-parts, nixpkgs, devshell, ... }: flake-parts.lib.mkFlake { inherit inputs; } ({ self, lib, ... }: {
    systems = [ "x86_64-linux" ];

    imports = [ devshell.flakeModule ];

    perSystem = { pkgs, system, ... }: {
      _module.args.pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
      };

      devshells.default = {
        devshell.name = "skys-the-limit";
        commands = [
          { name = "gradlew"; command = "cd $PRJ_ROOT; exec ./gradlew $@"; help = "gradle wrapper"; }
          { name = "client"; command = "gradlew runClient"; help = "run Minecraft client"; category = "shortcut"; }
          { name = "server"; command = "gradlew runServer"; help = "run Minecraft server"; category = "shortcut"; }
          { name = "datagen"; command = "gradlew runDatagen"; help = "run mod datagen"; category = "shortcut"; }
          { name = "build"; command = "gradlew build"; help = "build mod jar"; category = "shortcut"; }
        ];
        env = [
          { name = "LD_LIBRARY_PATH"; value = "${pkgs.libGL}/lib/:${pkgs.udev}/lib/"; }
        ];
      };
    };
  });
}
