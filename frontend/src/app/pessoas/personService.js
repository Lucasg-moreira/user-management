const apiUrl = process.env.NEXT_PUBLIC_API_URL + "/client"

export const createPerson = async (data) => {

    if (data.personType == 'pj')  
        apiUrl + '/company'
    else
        apiUrl + '/individual'
    
    const res = await fetch(apiUrl + "/auth", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
}