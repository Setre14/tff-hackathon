{
  description = "TFF hackathon development environment";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/release-23.05";
  inputs.systems.url = "github:nix-systems/default";

  outputs = {
    self,
    nixpkgs,
    flake-utils,
    systems,
  }:
    flake-utils.lib.eachSystem (import systems)
    (system: let
      pkgs = import nixpkgs {
        inherit system;
      };
    in {
      devShells.default = pkgs.mkShell {
        buildInputs = [
          pkgs.nodejs_20
          pkgs.nodePackages.typescript
          pkgs.nodePackages.typescript-language-server
          pkgs.python3
          pkgs.python3.pkgs.pip
          pkgs.docker-compose
        ];
          shellHook = ''
            # Tells pip to put packages into $PIP_PREFIX instead of the usual locations.
            # See https://pip.pypa.io/en/stable/user_guide/#environment-variables.
            export PIP_PREFIX=$(pwd)/_build/pip_packages
            export PYTHONPATH="$PIP_PREFIX/${pkgs.python3.sitePackages}:$PYTHONPATH"
            export PATH="$PIP_PREFIX/bin:$PATH"
            unset SOURCE_DATE_EPOCH
          '';
      };
    });
}
