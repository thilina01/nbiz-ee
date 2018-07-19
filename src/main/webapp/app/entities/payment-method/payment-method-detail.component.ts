import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentMethod } from 'app/shared/model/payment-method.model';

@Component({
    selector: 'jhi-payment-method-detail',
    templateUrl: './payment-method-detail.component.html'
})
export class PaymentMethodDetailComponent implements OnInit {
    paymentMethod: IPaymentMethod;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentMethod }) => {
            this.paymentMethod = paymentMethod;
        });
    }

    previousState() {
        window.history.back();
    }
}
