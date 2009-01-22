/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.swfrecords.actions;

import java.io.IOException;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;


/*
 * This class contains methods for reading action records.
 */
final class ActionReader {
  private ActionReader() {
    // prohibits instantiation
  }

  /*
   * Reads an action record from a bit stream.
   */
  static Action readRecord(InputBitStream stream) throws IOException, InvalidCodeException {
    Action action;
    InputBitStream actionStream = null; // contains action record data
    int offset                  = (int) stream.getOffset();
    short actionCode            = stream.readUI8();
    boolean longHeader          = (actionCode >= 0x80);
    int length                  = longHeader ? stream.readUI16() : 0;
    
    if (length > 0) {
      actionStream = new InputBitStream(stream.readBytes(length));
      actionStream.setANSI(stream.isANSI());
      actionStream.setShiftJIS(stream.isShiftJIS());
    }
    
    ActionType actionType;
    try {
      actionType = ActionType.lookup(actionCode);
    } catch (InvalidCodeException e) {
      actionType = ActionType.UNKNOWN_ACTION;
    }
    switch (actionType) {
      case ADD:
        action = new Add();
        break;
      case ADD_2:
        action = new Add2();
        break;
      case AND:
        action = new And();
        break;
      case ASCII_TO_CHAR:
        action = new AsciiToChar();
        break;
      case BIT_AND:
        action = new BitAnd();
        break;
      case BIT_L_SHIFT:
        action = new BitLShift();
        break;
      case BIT_OR:
        action = new BitOr();
        break;
      case BIT_R_SHIFT:
        action = new BitRShift();
        break;
      case BIT_U_R_SHIFT:
        action = new BitURShift();
        break;
      case BIT_XOR:
        action = new BitXor();
        break;
      case CALL:
        // MM bug here: high bit of code set although length=0
        action = new Call();
        break;
      case CALL_FUNCTION:
        action = new CallFunction();
        break;
      case CALL_METHOD:
        action = new CallMethod();
        break;
      case CAST_OP:
        action = new CastOp();
        break;
      case CHAR_TO_ASCII:
        action = new CharToAscii();
        break;
      case CLONE_SPRITE:
        action = new CloneSprite();
        break;
      case CONSTANT_POOL:
        action = new ConstantPool(actionStream);
        break;
      case DECREMENT:
        action = new Decrement();
        break;
      case DEFINE_FUNCTION:
        action = new DefineFunction(actionStream, stream);
        break;
      case DEFINE_FUNCTION_2:
        action = new DefineFunction2(actionStream, stream);
        break;
      case DEFINE_LOCAL:
        action = new DefineLocal();
        break;
      case DEFINE_LOCAL_2:
        action = new DefineLocal2();
        break;
      case DELETE:
        action = new Delete();
        break;
      case DELETE_2:
        action = new Delete2();
        break;
      case DIVIDE:
        action = new Divide();
        break;
      case END:
        action = new End();
        break;
      case END_DRAG:
        action = new EndDrag();
        break;
      case ENUMERATE:
        action = new Enumerate();
        break;
      case ENUMERATE_2:
        action = new Enumerate2();
        break;
      case EQUALS:
        action = new Equals();
        break;
      case EQUALS_2:
        action = new Equals2();
        break;
      case EXTENDS:
        action = new Extends();
        break;
      case GET_MEMBER:
        action = new GetMember();
        break;
      case GET_PROPERTY:
        action = new GetProperty();
        break;
      case GET_TIME:
        action = new GetTime();
        break;
      case GET_URL:
        action = new GetURL(actionStream);
        break;
      case GET_URL_2:
        action = new GetURL2(actionStream);
        break;
      case GET_VARIABLE:
        action = new GetVariable();
        break;
      case GO_TO_FRAME:
        action = new GoToFrame(actionStream);
        break;
      case GO_TO_FRAME_2:
        action = new GoToFrame2(actionStream);
        break;
      case GO_TO_LABEL:
        action = new GoToLabel(actionStream);
        break;
      case GREATER:
        action = new Greater();
        break;
      case IF:
        action = new If(actionStream);
        break;
      case IMPLEMENTS_OP:
        action = new ImplementsOp();
        break;
      case INCREMENT:
        action = new Increment();
        break;
      case INIT_ARRAY:
        action = new InitArray();
        break;
      case INIT_OBJECT:
        action = new InitObject();
        break;
      case INSTANCE_OF:
        action = new InstanceOf();
        break;
      case JUMP:
        action = new Jump(actionStream);
        break;
      case LESS:
        action = new Less();
        break;
      case LESS_2:
        action = new Less2();
        break;
      case M_B_ASCII_TO_CHAR:
        action = new MBAsciiToChar();
        break;
      case M_B_CHAR_TO_ASCII:
        action = new MBCharToAscii();
        break;
      case M_B_STRING_EXTRACT:
        action = new MBStringExtract();
        break;
      case M_B_STRING_LENGTH:
        action = new MBStringLength();
        break;
      case MODULO:
        action = new Modulo();
        break;
      case MULTIPLY:
        action = new Multiply();
        break;
      case NEW_METHOD:
        action = new NewMethod();
        break;
      case NEW_OBJECT:
        action = new NewObject();
        break;
      case NEXT_FRAME:
        action = new NextFrame();
        break;
      case NOT:
        action = new Not();
        break;
      case OR:
        action = new Or();
        break;
      case PLAY:
        action = new Play();
        break;
      case POP:
        action = new Pop();
        break;
      case PREVIOUS_FRAME:
        action = new PreviousFrame();
        break;
      case PUSH:
        action = new Push(actionStream);
        break;
      case PUSH_DUPLICATE:
        action = new PushDuplicate();
        break;
      case RANDOM_NUMBER:
        action = new RandomNumber();
        break;
      case REMOVE_SPRITE:
        action = new RemoveSprite();
        break;
      case RETURN:
        action = new Return();
        break;
      case SET_MEMBER:
        action = new SetMember();
        break;
      case SET_PROPERTY:
        action = new SetProperty();
        break;
      case SET_TARGET:
        action = new SetTarget(actionStream);
        break;
      case SET_TARGET_2:
        action = new SetTarget2();
        break;
      case SET_VARIABLE:
        action = new SetVariable();
        break;
      case STACK_SWAP:
        action = new StackSwap();
        break;
      case START_DRAG:
        action = new StartDrag();
        break;
      case STOP:
        action = new Stop();
        break;
      case STOP_SOUNDS:
        action = new StopSounds();
        break;
      case STORE_REGISTER:
        action = new StoreRegister(actionStream);
        break;
      case STRICT_EQUALS:
        action = new StrictEquals();
        break;
      case STRING_ADD:
        action = new StringAdd();
        break;
      case STRING_EQUALS:
        action = new StringEquals();
        break;
      case STRING_EXTRACT:
        action = new StringExtract();
        break;
      case STRING_GREATER:
        action = new StringGreater();
        break;
      case STRING_LENGTH:
        action = new StringLength();
        break;
      case STRING_LESS:
        action = new StringLess();
        break;
      case SUBTRACT:
        action = new Subtract();
        break;
      case TARGET_PATH:
        action = new TargetPath();
        break;
      case THROW:
        action = new Throw();
        break;
      case TOGGLE_QUALITY:
        action = new ToggleQuality();
        break;
      case TO_INTEGER:
        action = new ToInteger();
        break;
      case TO_NUMBER:
        action = new ToNumber();
        break;
      case TO_STRING:
        action = new ToString();
        break;
      case TRACE:
        action = new Trace();
        break;
      case TRY:
        action = new Try(actionStream, stream);
        break;
      case TYPE_OF:
        action = new TypeOf();
        break;
      case WAIT_FOR_FRAME:
        action = new WaitForFrame(actionStream);
        break;
      case WAIT_FOR_FRAME_2:
        action = new WaitForFrame2(actionStream);
        break;
      case WITH:
        action = new With(actionStream, stream);
        break;
      case UNKNOWN_ACTION:
        action = new UnknownAction(actionStream, actionCode);
        break;
      default:
        throw new AssertionError("Action type '" + actionType.name() + "' not handled!");
    }

    // fool a decompilation protection which pretends an action record to be
    // longer than it actually is in order to hide subsequent actions
    int delta = action.getSize() - (length + (longHeader ? 3 : 1));
    if (delta < 0) {
      stream.move(delta);
    }
    action.setOffset(offset);
    return action;
  }
}
