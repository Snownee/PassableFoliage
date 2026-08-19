# Segurança

## Escopo

O mod não implementa comunicação de rede própria, execução de processos, carregamento de bibliotecas nativas ou download de conteúdo em tempo de execução.

A configuração consome somente os campos documentados, ignora campos adicionais, possui limite de 64 KiB, usa JSON estrito, valida faixas numéricas e é gravada por substituição atômica. Configurações inválidas não são sobrescritas automaticamente.

O pacote-fonte mantém apenas o JAR oficial do Gradle Wrapper. A distribuição Gradle possui checksum SHA-256 fixado em `gradle-wrapper.properties`.

## Relato de vulnerabilidade

Não publique detalhes exploráveis em uma issue aberta. Entre primeiro em contato com o mantenedor do fork pelo canal privado disponível no repositório. Caso não exista canal privado, abra uma issue sem prova de conceito e solicite uma forma segura de envio.
