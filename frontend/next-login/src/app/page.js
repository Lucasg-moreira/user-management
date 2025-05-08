"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    if (sessionStorage.getItem('token'))
      router.push('/pessoas')
    else
      router.push("/login");
  }, [router]);

  return null;
}
