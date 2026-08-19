# Changelog

## 26.2.0

- Portado para Minecraft Java 26.2 estável e Java 25.
- Atualizados Fabric Loader, Fabric API, Loom e Gradle.
- Removida a dependência Kiwi e implementados configuração, registro e condições de recursos nativos.
- Adicionada migração da configuração YAML antiga para JSON.
- Corrigida a lógica de ocultação de etiquetas de nome.
- Corrigida a desaceleração indevida quando `alwaysLeafWalking` estava ativo.
- Preservados em memória os valores carregados quando a criação do arquivo de configuração falha.
- Corrigida a restauração do estado temporário de colisão quando uma chamada lança exceção.
- Removido logging excessivo no processamento de movimento das entidades.
- Atualizados datagen, tags e formato de recursos para 26.2.
- Removidos binários e arquivos auxiliares desnecessários do pacote-fonte.
- Fixadas versões de dependências e adicionado checksum da distribuição Gradle.
- Mantido suporte a Fabric e Quilt, em cliente e servidor.
