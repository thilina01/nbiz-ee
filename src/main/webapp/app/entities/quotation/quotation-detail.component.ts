import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuotation } from 'app/shared/model/quotation.model';

@Component({
    selector: 'jhi-quotation-detail',
    templateUrl: './quotation-detail.component.html'
})
export class QuotationDetailComponent implements OnInit {
    quotation: IQuotation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
        });
    }

    previousState() {
        window.history.back();
    }
}
