"use client";

import { useRouter } from "next/navigation";

export default function Home() {
  let router = useRouter()

  if (sessionStorage.getItem('refresh_token')) {
    router.push('/pessoas')
  }
  else {
    router.push('/login')
  }
  return null;
}
