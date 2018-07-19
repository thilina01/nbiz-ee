import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from './payment-method.service';

@Component({
    selector: 'jhi-payment-method-update',
    templateUrl: './payment-method-update.component.html'
})
export class PaymentMethodUpdateComponent implements OnInit {
    private _paymentMethod: IPaymentMethod;
    isSaving: boolean;

    constructor(private paymentMethodService: PaymentMethodService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentMethod }) => {
            this.paymentMethod = paymentMethod;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paymentMethod.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentMethodService.update(this.paymentMethod));
        } else {
            this.subscribeToSaveResponse(this.paymentMethodService.create(this.paymentMethod));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethod>>) {
        result.subscribe((res: HttpResponse<IPaymentMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get paymentMethod() {
        return this._paymentMethod;
    }

    set paymentMethod(paymentMethod: IPaymentMethod) {
        this._paymentMethod = paymentMethod;
    }
}
