const apiUrl = process.env.NEXT_PUBLIC_API_URL

export let user = {}


export async function login(data) {
  const res = await fetch(apiUrl + "/auth", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("Failed to create client");

  return res.json();
}

export async function getRefreshedToken(data) {
  console.log (data)
  const res = await fetch(apiUrl + "/auth/refresh", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    return false
  };

  return res.json();
}
