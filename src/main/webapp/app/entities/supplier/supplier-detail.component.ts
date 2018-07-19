import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplier } from 'app/shared/model/supplier.model';

@Component({
    selector: 'jhi-supplier-detail',
    templateUrl: './supplier-detail.component.html'
})
export class SupplierDetailComponent implements OnInit {
    supplier: ISupplier;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplier }) => {
            this.supplier = supplier;
        });
    }

    previousState() {
        window.history.back();
    }
}
