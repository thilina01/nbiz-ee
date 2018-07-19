import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from './quotation.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-quotation-update',
    templateUrl: './quotation-update.component.html'
})
export class QuotationUpdateComponent implements OnInit {
    private _quotation: IQuotation;
    isSaving: boolean;

    customers: ICustomer[];
    quotationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private quotationService: QuotationService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
        });
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.quotation.quotationDate = moment(this.quotationDate, DATE_TIME_FORMAT);
        if (this.quotation.id !== undefined) {
            this.subscribeToSaveResponse(this.quotationService.update(this.quotation));
        } else {
            this.subscribeToSaveResponse(this.quotationService.create(this.quotation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuotation>>) {
        result.subscribe((res: HttpResponse<IQuotation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
    get quotation() {
        return this._quotation;
    }

    set quotation(quotation: IQuotation) {
        this._quotation = quotation;
        this.quotationDate = moment(quotation.quotationDate).format(DATE_TIME_FORMAT);
    }
}
