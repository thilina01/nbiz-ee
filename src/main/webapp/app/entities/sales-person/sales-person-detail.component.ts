import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalesPerson } from 'app/shared/model/sales-person.model';

@Component({
    selector: 'jhi-sales-person-detail',
    templateUrl: './sales-person-detail.component.html'
})
export class SalesPersonDetailComponent implements OnInit {
    salesPerson: ISalesPerson;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salesPerson }) => {
            this.salesPerson = salesPerson;
        });
    }

    previousState() {
        window.history.back();
    }
}
