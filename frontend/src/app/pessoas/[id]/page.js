"use client";

import { useParams, useRouter } from "next/navigation";
import NewPersonPage from "../nova/page";
import { useEffect, useState } from "react";
import { api } from "@/utils/api";

const apiUrl = process.env.NEXT_PUBLIC_API_URL;

export default function Edit() {
    const router = useRouter()

    const { id } = useParams()

    const [client, setClient] = useState({})

    useEffect(() => {
        async function fetchData() {
            if (!client) {
                return; x
            }
            const response = await api.get()
            console.log('fetchData', response)

            setClient(response)
        }

        fetchData()
    }, [])

    console.log(id)
    return (
        <>
            {client ? 
                <NewPersonPage
                data={client}
                
            />: <p>teste</p>}

        </>

    )
}