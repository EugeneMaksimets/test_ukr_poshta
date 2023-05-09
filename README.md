# test_ukr_poshta
API to use: 
PERSON: 

    /person/create

    /person/update

    /person/delete {QuerryParam = id}

    /person {QuerryParam = id}

    /person/all

LEVEL:

    /level/create
    
    /level/delete {QuerryParam = id}
    
    /level/all/name {QuerryParam = level} 

    /level/update

    /level/set/person {QuerryParam = id, QuerryParam = level}

    (QuerryParam level - string name like junior)
    
PROJECT: 

    /project/create

    /project/update

    /project/add/person{QuerryParam = id, QuerryParam = idProject}

    /project/delete {QuerryParam = id}

    /project {QuerryParam = id}

    /project/all
    
ROLE: 

    /role/create
    
    /role/delete {QuerryParam = id}
    
    /role {QuerryParam = id}

    /role/all

    /role/update
    
    /role/set/person {QuerryParam = id, QuerryParam = roleId}

    /role/name {QuerryParam = role}

    (QuerryParam role - string name role like developer)
    
TEAM: 

    /team/create
    
    /team/update
    
    /team/delete {QuerryParam = id}
    
    /team {QuerryParam = id}
    
    /team/all
    
    team/add/person/ {QuerryParam = id, QuerryParam = teamId}
    
    team/{id}/add/person
    
    team/delete/person/ {QuerryParam = id, QuerryParam = teamId}
