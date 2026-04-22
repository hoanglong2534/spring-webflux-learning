CREATE TABLE users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255),
   age BIGINT,
   address VARCHAR(255)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    price DOUBLE,

    FOREIGN KEY (user_id) REFERENCES users(id)
);