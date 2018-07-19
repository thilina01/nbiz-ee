import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { SalesPersonService } from './sales-person.service';

@Component({
    selector: 'jhi-sales-person-update',
    templateUrl: './sales-person-update.component.html'
})
export class SalesPersonUpdateComponent implements OnInit {
    private _salesPerson: ISalesPerson;
    isSaving: boolean;

    constructor(private salesPersonService: SalesPersonService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salesPerson }) => {
            this.salesPerson = salesPerson;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salesPerson.id !== undefined) {
            this.subscribeToSaveResponse(this.salesPersonService.update(this.salesPerson));
        } else {
            this.subscribeToSaveResponse(this.salesPersonService.create(this.salesPerson));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISalesPerson>>) {
        result.subscribe((res: HttpResponse<ISalesPerson>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get salesPerson() {
        return this._salesPerson;
    }

    set salesPerson(salesPerson: ISalesPerson) {
        this._salesPerson = salesPerson;
    }
}
