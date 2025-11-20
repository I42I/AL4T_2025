{
  description = "Dev shell for Architecture Software";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

    # For packaging
    gradle2nix.url = "github:tadfisher/gradle2nix/v2";
  };

  outputs =
    {
      self,
      nixpkgs,
      gradle2nix,
      ...
    }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = [
          pkgs.openjdk17
          pkgs.gradle
        ];

        # keep gradle caches local in the repo
        shellHook = ''
          export JAVA_HOME=${pkgs.openjdk17}
          export GRADLE_USER_HOME="$PWD/.gradle"
          echo "Run tests: ./gradlew :basics:test or ./gradlew :mario:test"
        '';
      };

      packages.${system}.gradle2nix-example = gradle2nix.builders.${system}.buildGradlePackage {
        pname = "ecam-project";
        version = "0.1.0";
        src = ./.;
        lockFile = ./gradle.lock;
        gradleBuildFlags = [ ":mario:shadowJar" ];
      };
    };
}
