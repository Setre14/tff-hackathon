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

          pkgs.docker-compose
        ];
      };
    });
}
