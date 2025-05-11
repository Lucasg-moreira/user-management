'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { getRefreshedToken } from '../login/loginService'

export const validateCookieDate = (refreshToken) => {
    let expirationDate = new Date(refreshToken.tokenExpiration)
    let refreshExpirationDate = new Date(refreshToken.refreshExpiration)

    let now = new Date().getTime()

    return (now < expirationDate.getTime() && now < refreshExpirationDate.getTime())
}

export const getAccessToken = () => {
    let refreshToken = sessionStorage.getItem('refresh_token')

    if (!refreshToken)
        return false

    return JSON.parse(refreshToken)
}

export default function ClientSessionCheck({ children }) {
    const router = useRouter()

    useEffect(() => {
        let token = getAccessToken()

        if (validateCookieDate(token)) {
            getRefreshedToken(token)
        }
        else {
            router.push('/login')
        }

        return () => {}

    }, [router])

    return <>{children}</>
}