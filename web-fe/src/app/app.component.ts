import { Component } from '@angular/core';
import { LayoutHeaderComponent } from "./core/layout/layout-header/layout-header.component";
import { LayoutFooterComponent } from "./core/layout/layout-footer/layout-footer.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LayoutHeaderComponent, LayoutFooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'web-fe';
}
