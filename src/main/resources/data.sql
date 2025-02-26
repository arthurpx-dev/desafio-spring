CREATE TABLE pessoa (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(255) NOT NULL,
    pessoa_fisica BOOLEAN,
    endereco_logradouro VARCHAR(255),
    endereco_numero VARCHAR(50),
    endereco_complemento VARCHAR(255),
    endereco_bairro VARCHAR(255),
    endereco_cidade VARCHAR(255),
    endereco_uf VARCHAR(2),
    endereco_cep VARCHAR(10),
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP
);


CREATE TABLE banco (
      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP
);

CREATE TABLE empresa (
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    razao_social VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL,
    endereco_logradouro VARCHAR(255),
    endereco_numero VARCHAR(50),
    endereco_complemento VARCHAR(255),
    endereco_bairro VARCHAR(255),
    endereco_cidade VARCHAR(255),
    endereco_uf VARCHAR(2),
    endereco_cep VARCHAR(10),
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP
);

CREATE TABLE conta (
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    agencia VARCHAR(10),
    conta VARCHAR(20),
    digito_agencia VARCHAR(2),
    digito_conta VARCHAR(2),
    banco_id BIGINT,
    empresa_id BIGINT,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    FOREIGN KEY (banco_id) REFERENCES banco(id),
    FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE convenio (
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero_contrato VARCHAR(50),
    carteira VARCHAR(10),
    variacao_carteira VARCHAR(10),
    juros_porcentagem DECIMAL(5, 2),
    multa_porcentagem DECIMAL(5, 2),
    conta_id BIGINT,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    FOREIGN KEY (conta_id) REFERENCES conta(id)
);


CREATE TABLE fatura (
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    valor DECIMAL(10, 2),
    data_vencimento DATE,
    tipo VARCHAR(20),
    tipo_pagamento VARCHAR(20),
    situacao VARCHAR(20),
    numero_documento VARCHAR(50),
    nosso_numero VARCHAR(50),
    conta_id BIGINT,
    convenio_id BIGINT,
    pessoa_id BIGINT,
    criado_em TIMESTAMP,
    atualizado_em TIMESTAMP,
    FOREIGN KEY (conta_id) REFERENCES conta(id),
    FOREIGN KEY (convenio_id) REFERENCES convenio(id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);



INSERT INTO pessoa (nome, documento, pessoa_fisica, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_uf, endereco_cep, criado_em, atualizado_em) 
VALUES ('Maria Souza', '98765432100', true, 'Avenida B', '456', 'Casa 2', 'Jardins', 'Rio de Janeiro', 'RJ', '22000-000', NOW(), NOW());

INSERT INTO banco (codigo, nome, criado_em, atualizado_em)
VALUES ('001', 'Banco do Brasil', NOW(), NOW());

INSERT INTO empresa (razao_social, cnpj, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_uf, endereco_cep, criado_em, atualizado_em)
VALUES ('Empresa Exemplo LTDA', '12.345.678/0001-99', 'Rua Exemplo', '123', 'Sala 101', 'Centro', 'São Paulo', 'SP', '01000-000', NOW(), NOW());

INSERT INTO conta (agencia, conta, digito_agencia, digito_conta, banco_id, empresa_id, criado_em, atualizado_em)
VALUES ('1234', '56789', 'X', '9', 1, 1, NOW(), NOW());

INSERT INTO convenio (numero_contrato, carteira, variacao_carteira, juros_porcentagem, multa_porcentagem, conta_id, criado_em, atualizado_em)
VALUES ('123456', '001', '01', 2.5, 5.0, 1, NOW(), NOW());

INSERT INTO fatura (
    conta_id,
    valor,
    data_vencimento,
    tipo,
    tipo_pagamento,
    situacao,
    numero_documento,
    nosso_numero,
    convenio_id,
    pessoa_id,
    criado_em,
    atualizado_em
)
VALUES (
    1, 
    100.00, 
    '2024-03-01', 
    'RECEITA', 
    'BOLETO', 
    'NAO_PAGA',
    '123456', 
    '987654', 
    1, 
    1, 
    NOW(), 
    NOW()
);
