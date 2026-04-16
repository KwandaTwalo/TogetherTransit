// 1. Import axios library
// Axios is a promise-based HTTP client that lets us call our backend APIs easily.
import axios from 'axios';

// 2. Create an axios instance
// This sets up a reusable "api" object with a base URL pointing to your Spring Boot backend.
// Using your PC's IP address (192.168.137.1) instead of localhost.
// Why? Because Expo Go runs on your phone, so it needs your PC's IP to reach the backend.
const api = axios.create({
  baseURL: "http://192.168.137.1:8080/togetherTransit",
  timeout: 30000, // Increased from 10000ms to 30000ms (30 seconds) to fix timeout errors
  headers: {
    'Content-Type': 'application/json',
  },
});

// 3. Driver signup
export const signupDriver = async (
  firstName: string,
  lastName: string,
  email: string,
  password: string,
  licenseNumber: string
) => {
  try {
    const driverDTO = {
      firstName,
      lastName,
      licenseNumber,
      ratingAverage: 1,
      createdAt: new Date().toISOString().split('T')[0],  // Added createdAt as required by DriverDTO validation
      authentication: {
        emailAddress: email,
        password: password,
        lastLogin: null,
        locked: false,
      },
    };

    const response = await api.post('/driver/create', driverDTO);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// 4. Parent signup
export const signupParent = async (
  firstName: string,
  lastName: string,
  email: string,
  password: string
) => {
  try {
    const parentDTO = {
      firstName,
      lastName,
      createdAt: new Date().toISOString().split('T')[0],
      authentication: {
        emailAddress: email,
        password: password,
        lastLogin: null,
        locked: false,
      },
    };

    const response = await api.post('/parent/create', parentDTO);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// 5. Login (for both drivers and parents)
export const login = async (email: string, password: string) => {
  try {
    const loginDTO = {
      emailAddress: email,
      password: password,
    };

    const response = await api.post('/authentication/login', loginDTO);
    return response.data; // Return Authentication object.
  } catch (error) {
    throw error;  
    }
  };

  // 6. Get user by email (to determine role after login)
export const getUserByEmail = async (email: string) => {
  try {
    // Try driver first.
    const driverResponse = await api.get(`/driver/findByEmail/${email}`);
    if (driverResponse.data) {
      return { role: 'driver', data: driverResponse.data };
    }
  } catch (error) {
    // Driver not found, continue to parent
  }

  // Try parent next.
  try {
    const parentResponse = await api.get(`/parent/findByEmail/${email}`);
    if (parentResponse.data) {
      return { role: 'parent', data: parentResponse.data };
    }
  } catch (error) {
    // Parent not found
  }

  // If no user found, throw an error.
  throw new Error("User not found");
};


// 7. Export the axios instance
// This lets you import "api" anywhere in your React Native app and make requests like:
// api.get("/address/getAllAddresses") or api.post("/driver/create", data)
export default api;