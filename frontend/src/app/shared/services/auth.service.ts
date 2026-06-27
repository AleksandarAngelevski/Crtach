import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from '../../models/register-request.model';
import { LoginRequest } from '../../models/login-request.model';

@Injectable( {providedIn: 'root'} )
export class AuthService {
    private baseUrl = 'http://localhost:8080/api/users';

    constructor(private http: HttpClient){}

    register(dto: RegisterRequest){
        return this.http.post(`${this.baseUrl}`, dto);
    }

    login(dto: LoginRequest){
        return this.http.post(`${this.baseUrl}`, dto);  
    }
}