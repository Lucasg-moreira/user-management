"use client";

import { createContext, useContext, useState, useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import { api } from '@/utils/api';

const apiUrl = process.env.NEXT_PUBLIC_API_URL;

const AuthContext = createContext({});

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();
  const pathname = usePathname();

  useEffect(() => {
    const token = sessionStorage.getItem('refresh_token');
    const userData = sessionStorage.getItem('user');
    
    if (token && userData) {
      setUser(JSON.parse(userData));
    }
    
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      
      const response = await api.post(`${apiUrl}/auth`, { username, password });

      const refreshToken = {
        refresh_token: response.refresh_token,
        refreshExpiration: response.refreshExpiration,
        tokenExpiration: response.tokenExpiration,
        tokenExpiresIn: response.token_expires_in
      }

      sessionStorage.setItem('refresh_token', JSON.stringify(refreshToken));
      sessionStorage.setItem('user', JSON.stringify(response.user));

      setUser(response.user);
      
      return response.user;
    } catch (error) {
      console.error(error)
      throw new Error('Invalid credentials');
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    router.push('/login');
  };

  const isAuthenticated = () => {
    return !!user;
  };

  const isPublicRoute = (path) => {
    return ['/login', '/register', '/forgot-password'].includes(path);
  };

  useEffect(() => {
    if (!loading) {
      if (!isAuthenticated() && !isPublicRoute(pathname)) {
        router.push('/login');
      } else if (isAuthenticated() && isPublicRoute(pathname)) {
        router.push('/client');
      }
    }
  }, [loading, pathname]);

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 