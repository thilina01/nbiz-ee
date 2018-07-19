import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from './supplier.service';

@Component({
    selector: 'jhi-supplier-update',
    templateUrl: './supplier-update.component.html'
})
export class SupplierUpdateComponent implements OnInit {
    private _supplier: ISupplier;
    isSaving: boolean;

    constructor(private supplierService: SupplierService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplier }) => {
            this.supplier = supplier;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.supplier.id !== undefined) {
            this.subscribeToSaveResponse(this.supplierService.update(this.supplier));
        } else {
            this.subscribeToSaveResponse(this.supplierService.create(this.supplier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISupplier>>) {
        result.subscribe((res: HttpResponse<ISupplier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get supplier() {
        return this._supplier;
    }

    set supplier(supplier: ISupplier) {
        this._supplier = supplier;
    }
}
