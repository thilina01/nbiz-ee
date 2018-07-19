import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBrand } from 'app/shared/model/brand.model';

@Component({
    selector: 'jhi-brand-detail',
    templateUrl: './brand-detail.component.html'
})
export class BrandDetailComponent implements OnInit {
    brand: IBrand;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ brand }) => {
            this.brand = brand;
        });
    }

    previousState() {
        window.history.back();
    }
}
