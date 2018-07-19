import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuotationItem } from 'app/shared/model/quotation-item.model';

@Component({
    selector: 'jhi-quotation-item-detail',
    templateUrl: './quotation-item-detail.component.html'
})
export class QuotationItemDetailComponent implements OnInit {
    quotationItem: IQuotationItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotationItem }) => {
            this.quotationItem = quotationItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
