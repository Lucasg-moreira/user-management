import { serialize } from "cookie";
import { login, user } from "./loginService";

export async function submitClientForm(payload) {
    const res = await login(payload)

}