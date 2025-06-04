import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
// import { Recipe } from '../recipe.model';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private apiUrl = 'http://localhost:8080/api/recipes';

  constructor(private http: HttpClient) { }

  getAllRecipes(): Observable<any> {
    return this.http.get(`${this.apiUrl}`);
  }


  getRecipeById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getRecipesByCuisine(cuisine: string): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/recipes/cuisine/${cuisine}`);
}

  // getRecipesByCuisine(cuisine: string): Observable<any> {
  //   return this.http.get(`${this.apiUrl}/cuisine/${cuisine}`);
  // }

}
