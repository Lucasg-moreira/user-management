"use client";

import { useRouter } from "next/navigation";

export default function Home() {
  let router = useRouter()

  router.push("/login")
  return null;
}
