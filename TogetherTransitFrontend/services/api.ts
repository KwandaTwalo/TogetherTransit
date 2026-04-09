// 1. Import axios library
// Axios is a promise-based HTTP client that lets us call our backend APIs easily.
import axios from 'axios';

// 2. Create an axios instance
// This sets up a reusable "api" object with a base URL pointing to your Spring Boot backend.
// Replace <YOUR_PC_IP> with your actual machine IP address (e.g., 192.168.0.101).
// Why not localhost? Because Expo Go runs on your phone, so it needs your PC's IP to reach the backend.
const api = axios.create({
  baseURL: "http://192.168.137.1:8080/togetherTransit", 
});

// 3. Export the axios instance
// This lets you import "api" anywhere in your React Native app and make requests like:
// api.get("/address/getAllAddresses") or api.post("/address/create", data)
export default api;