import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HotlistRefCode } from './hotlist-ref-code.model';
import { HotlistRefCodeService } from './hotlist-ref-code.service';

@Injectable()
export class HotlistRefCodePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hotlistRefCodeService: HotlistRefCodeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.hotlistRefCodeService.find(id).subscribe((hotlistRefCode) => {
                this.hotlistRefCodeModalRef(component, hotlistRefCode);
            });
        } else {
            return this.hotlistRefCodeModalRef(component, new HotlistRefCode());
        }
    }

    hotlistRefCodeModalRef(component: Component, hotlistRefCode: HotlistRefCode): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hotlistRefCode = hotlistRefCode;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
