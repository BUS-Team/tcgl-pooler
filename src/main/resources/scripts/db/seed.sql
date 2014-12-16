CREATE TABLE unprocessed_position
  ( 
     id            SERIAL NOT NULL, 
     bus_line      INTEGER, 
     date_received TIMESTAMP WITH TIME zone, 
     content       CHARACTER varying, 
     CONSTRAINT pk_response_positions PRIMARY KEY (id) 
  ) with (oids=FALSE); 

CREATE TABLE position
  ( 
     id            SERIAL NOT NULL, 
     bus_line      INTEGER, 
     bus_direction CHARACTER varying, 
     date_received TIMESTAMP WITH TIME zone, 
     latitude      FLOAT, 
     longitude     FLOAT,
     type          INTEGER,
     CONSTRAINT pk_position PRIMARY KEY (id)
  ) with (oids=FALSE); 

CREATE TABLE last_unprocessed_position_processed
  ( 
     id      SERIAL NOT NULL, 
     last_id BIGINT, 
     CONSTRAINT pk_last_unprocessed_position_processed PRIMARY KEY(id)
  ) with (oids=FALSE); 

INSERT INTO last_unprocessed_position_processed
            (last_id) 
VALUES      (0); 