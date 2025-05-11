"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { getAccessToken, validateCookieDate } from "./components/clientSessionCheck";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    let accessToken = getAccessToken()

    if (!accessToken) {
      router.push('/login')
      return
    }

    if (validateCookieDate(accessToken))
      router.push('/pessoas')
    else
      router.push('/login');

  }, [router]);

  return null;
}
