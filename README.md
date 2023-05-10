# test_ukr_poshta

Web API application for storing data in a database about persons, teams, roles, levels, and projects is implemented using the Spring library. H2 database is used, and tables are described in schema.sql. To use data.sql and create standard levels and roles, if these two files are deleted, the database will be built using Hibernate.

Tested in postman

API to use: 
PERSON: 

    /person/create
    (Body: json)

    /person/update
    (Body json with id)

    /person/delete {QuerryParam = id}

    /person {QuerryParam = id}

    /person/all

LEVEL:

    /level/create
    (Body: json)
    
    /level/update
    (Body: json with id)
    
    /level/delete {QuerryParam = id}
    
    /level/all/name {QuerryParam = level} 

    /level/set/person {QuerryParam = id, QuerryParam = level}

    (QuerryParam level - string name like junior)
    
PROJECT: 

    /project/create
    (Body: json)

    /project/update
    (Body: json with id)

    /project/add/person{QuerryParam = id, QuerryParam = idProject}

    /project/delete {QuerryParam = id}

    /project {QuerryParam = id}

    /project/all
    
ROLE: 

    /role/create
    (Body: json)
    
    /role/update
    (Body: json with id)
    
    /role/delete {QuerryParam = id}
    
    /role {QuerryParam = id}

    /role/all
    
    /role/set/person {QuerryParam = id, QuerryParam = roleId}

    /role/name {QuerryParam = role}

    (QuerryParam role - string name role like developer)
    
TEAM: 

    /team/create
    (Body: json)
    
    /team/update
    (Body: json with id)
    
    /team/delete {QuerryParam = id}
    
    /team {QuerryParam = id}
    
    /team/all
    
    team/add/person/ {QuerryParam = id, QuerryParam = teamId}
    
    team/add/person/team {QuerryParam = id}
    (Body json)
    
    team/delete/person/ {QuerryParam = id, QuerryParam = teamId}
