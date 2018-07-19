import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ISaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';
import { SaleInvoicePaymentService } from './sale-invoice-payment.service';
import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';
import { SaleInvoiceService } from 'app/entities/sale-invoice';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from 'app/entities/payment-method';

@Component({
    selector: 'jhi-sale-invoice-payment-update',
    templateUrl: './sale-invoice-payment-update.component.html'
})
export class SaleInvoicePaymentUpdateComponent implements OnInit {
    private _saleInvoicePayment: ISaleInvoicePayment;
    isSaving: boolean;

    saleinvoices: ISaleInvoice[];

    paymentmethods: IPaymentMethod[];
    paymentDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private saleInvoicePaymentService: SaleInvoicePaymentService,
        private saleInvoiceService: SaleInvoiceService,
        private paymentMethodService: PaymentMethodService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saleInvoicePayment }) => {
            this.saleInvoicePayment = saleInvoicePayment;
        });
        this.saleInvoiceService.query().subscribe(
            (res: HttpResponse<ISaleInvoice[]>) => {
                this.saleinvoices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.paymentMethodService.query().subscribe(
            (res: HttpResponse<IPaymentMethod[]>) => {
                this.paymentmethods = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.saleInvoicePayment.paymentDate = moment(this.paymentDate, DATE_TIME_FORMAT);
        if (this.saleInvoicePayment.id !== undefined) {
            this.subscribeToSaveResponse(this.saleInvoicePaymentService.update(this.saleInvoicePayment));
        } else {
            this.subscribeToSaveResponse(this.saleInvoicePaymentService.create(this.saleInvoicePayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoicePayment>>) {
        result.subscribe((res: HttpResponse<ISaleInvoicePayment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSaleInvoiceById(index: number, item: ISaleInvoice) {
        return item.id;
    }

    trackPaymentMethodById(index: number, item: IPaymentMethod) {
        return item.id;
    }
    get saleInvoicePayment() {
        return this._saleInvoicePayment;
    }

    set saleInvoicePayment(saleInvoicePayment: ISaleInvoicePayment) {
        this._saleInvoicePayment = saleInvoicePayment;
        this.paymentDate = moment(saleInvoicePayment.paymentDate).format(DATE_TIME_FORMAT);
    }
}
