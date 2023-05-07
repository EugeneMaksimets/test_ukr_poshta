# test_ukr_poshta
API to use: 
PERSON: 

    /person/create

    /person/update

    /person/delete/{id}

    /person/{id}

    /person/all

LEVEL:

    /level/create
    
    /level/update
    
    /level/delete/{id}
    
    /level/{id}/level/all
    
    /level/{level}/set/person/{id}
    
PROJECT: 

    /project/create

    /project/update

    /project/{idProject}/add/person/{idPerson}

    /project/delete/{id}

    /project/{id}

    /project/all
    
ROLE: 

    /role/create

    /role/update
    
    /role/delete/{id}
    
    /role/{id}"

    /role/all
    
    /role/{role}/set/person/{id}
    
TEAM: 

    /team/create
    
    /team/update
    
    /team/delete/{id}
    
    /team/{id}
    
    /team/all
    
    team/{teamId}/add/person/{personId}
    
    team/{id}/add/person
    
    team/{teamId}/delete/person/{personId}
