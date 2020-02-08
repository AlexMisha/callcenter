CREATE USER callcenter_user PASSWORD 'fa7aa9aed8f1dba6fac0f0eaf06d6bf73d4655ea72b9909bf0e5d48b88dfe30c';
CREATE DATABASE callcenter WITH OWNER callcenter_user;
GRANT ALL ON DATABASE callcenter TO callcenter_user;