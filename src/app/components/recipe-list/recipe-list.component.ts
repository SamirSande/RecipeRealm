import { Component, OnInit } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';
import { Router } from '@angular/router';
// import { Recipe } from 'src/app/recipe.model';


@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes: any[] = [];
  selectedCuisine: string = '';
  cuisine: string='';
  allRecipes: any[] = [];

  constructor(private recipeService: RecipeService, private router: Router) { }


  ngOnInit(): void {
    this.recipeService.getAllRecipes().subscribe(data => {
      this.recipes = data;
      this.allRecipes = data;
    });
  }

  onCuisineChange(event: any): void {
    const selectedCuisine = event.target.value;
    if (selectedCuisine) {
        this.recipeService.getRecipesByCuisine(selectedCuisine).subscribe(data => {
            console.log('Filtered recipes:', data);
            this.recipes = data;
        });
    } else {
        this.recipes = [...this.allRecipes];
    }
}

  filterByCuisine(): void {
    if (this.cuisine) {
      this.recipeService.getRecipesByCuisine(this.cuisine).subscribe(data => {
        this.recipes = data;
      });
    } else {
      this.recipes = this.allRecipes;
    }
  }

  viewRecipeDetails(id: number): void {
    this.router.navigate(['/recipe', id]);
  }
}
