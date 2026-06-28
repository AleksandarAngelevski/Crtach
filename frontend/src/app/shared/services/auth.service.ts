import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from '../../models/register-request.model';
import { LoginRequest } from '../../models/login-request.model';
import { faThumbTackSlash } from '@fortawesome/free-solid-svg-icons';

@Injectable( {providedIn: 'root'} )
export class AuthService {
    private baseUrl = 'http://localhost:8080/api/users';
    private readonly TOKEN_KEY = 'auth_token';


    constructor(private http: HttpClient){}

    register(dto: RegisterRequest){
        return this.http.post(`${this.baseUrl}`, dto);
    }

    login(dto: LoginRequest){
        return this.http.post<{ token: string}>(`${this.baseUrl}/login`, dto);  
    }

    saveToken(token: string): void{
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    logout(): void {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    isLoggedIn(): boolean{
        return !!this.getToken();
    }
}